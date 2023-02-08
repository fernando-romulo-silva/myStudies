const path = require('path');
const CleanPlugin = require('clean-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
  mode: 'development',
  entry: './src/app.js',
  output: {
    filename: 'app.js',
    path: path.resolve(__dirname, 'assets', 'scripts'),
    publicPath: 'assets/scripts/',
    clean: true,
  },
  plugins: [
    new HtmlWebpackPlugin({
      title: 'Development',
    }),
   // new CleanPlugin.CleanWebpackPlugin()
  ],
  devtool: 'eval-cheap-source-map',
  devServer: {
    static: {
      directory: path.join(__dirname, '/'),
      watch: true,
    },
  },
};
