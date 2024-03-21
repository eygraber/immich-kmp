if(config.devServer) {
  config.devServer.historyApiFallback = {
    rewrites: [
      { from: /.*immich-kmp-wasm.wasm/, to: '/immich-kmp-wasm.wasm' },
    ]
  }
}
