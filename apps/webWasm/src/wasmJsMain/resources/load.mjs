import { instantiate } from './immich-kmp-wasm.uninstantiated.mjs';

await wasmSetup;

let te = null;
try {
    await instantiate({ skia: Module['asm'] });
} catch (e) {
  te = e;
}

if (te != null) {
    throw te;
}
