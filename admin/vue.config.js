'use strict';
module.exports={
  chainWebpack(config){
    config
      // https://webpack.js.org/configuration/devtool/#development
      .when(process.env.NODE_ENV === 'development',
        config => config.devtool('#eval-source-map')
      )
  }
};
