'use strict';
const merge = require('webpack-merge');
const prodEnv = require('./prod.env');

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  projectName:'jeecg-boot',
  domainUrl:'http://xudongyi.tunnel.qydev.com'
  //domainUrl:'http://127.0.0.1:8081'
});
