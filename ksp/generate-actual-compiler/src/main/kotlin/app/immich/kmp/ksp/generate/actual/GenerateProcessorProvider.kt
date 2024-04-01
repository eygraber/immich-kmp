package app.immich.kmp.ksp.generate.actual

import com.google.devtools.ksp.getVisibility
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.Modifier
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ksp.toKModifier
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import me.tatarka.inject.annotations.TargetComponentAccessor

class GenerateProcessor(
  private val codeGenerator: CodeGenerator,
  private val logger: KSPLogger,
) : SymbolProcessor {
  override fun process(resolver: Resolver): List<KSAnnotated> {
    val symbols = resolver.getSymbolsWithAnnotation(
      requireNotNull(TargetComponentAccessor::class.qualifiedName),
    )

    for(symbol in symbols) {
      val func = symbol as KSFunctionDeclaration
      val funcName = func.simpleName.asString()

      if(Modifier.ACTUAL in func.modifiers && func.findExpects().firstOrNull() != null) {
        continue
      }

      if(Modifier.EXPECT !in func.modifiers) {
        logger.error("$funcName should be an expect fun")
        continue
      }

      val funcReceiver = func.extensionReceiver
      val funcReceiverName = funcReceiver?.resolve()?.declaration?.qualifiedName?.asString()
      if(funcReceiver == null || funcReceiverName == null || !funcReceiverName.endsWith("Companion")) {
        logger.error("$funcName should be declared as an extension of a @Component's companion")
        continue
      }
      val funcReceiverType = funcReceiver.toTypeName()

      val funcReturn = func.returnType
      if(funcReturn == null ||
        funcReturn.toString() == "Unit" ||
        funcReturn.resolve().declaration.annotations.none { it.shortName.asString() == "Component" }
      ) {
        logger.error("$funcName's return type should be a type annotated with @Component")
        continue
      }

      val file = FileSpec.builder(
        packageName = func.packageName.asString(),
        fileName = "ActualInject${funcReturn.resolve().declaration.simpleName.asString()}",
      ).apply {
        addFunction(
          FunSpec.builder(funcName).apply {
            addAnnotation(TargetComponentAccessor::class)

            addModifiers(
              func.getVisibility().toKModifier() ?: KModifier.INTERNAL,
              KModifier.ACTUAL,
            )

            receiver(funcReceiverType)

            for(param in func.parameters) {
              val paramName = requireNotNull(param.name?.asString()) {
                "$funcName cannot have a parameter without a name"
              }

              addParameter(paramName, param.type.toTypeName())
            }

            val funcParams = func.parameters.joinToString { it.name?.asString().toString() }

            addCode(
              CodeBlock.of(
                "return %T.create($funcParams)",
                funcReceiverType,
              ),
            )

            returns(funcReturn.toTypeName())
          }.build(),
        )
      }.build()

      file.writeTo(codeGenerator, aggregating = true)
    }

    return emptyList()
  }
}

class GenerateProcessorProvider : SymbolProcessorProvider {
  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor = GenerateProcessor(
    environment.codeGenerator,
    environment.logger,
  )
}
