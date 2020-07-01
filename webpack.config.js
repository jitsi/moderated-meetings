/* eslint-disable @typescript-eslint/no-var-requires */
const path = require('path');
const TsconfigPathsPlugin = require('tsconfig-paths-webpack-plugin');

module.exports = {
    entry: {
        'app': './src/main/js/index.tsx'
    },
    mode: 'development',
    module: {
        rules: [
            {
                test: /\.ts(x?)$/,
                exclude: /node_modules/,
                use: [
                    {
                        loader: 'ts-loader'
                    }
                ]
            },
            {
                test: /\.css$/i,
                use: [ 'style-loader', 'css-loader' ]
            },
            {
                test: /\.s[ac]ss$/i,
                use: [
                    'style-loader',
                    'css-loader',
                    'sass-loader'
                ]
            }
        ]
    },
    output: {
        path: path.join(__dirname, 'public')
    },
    resolve: {
        extensions: [ '.js', '.ts', '.tsx', '.jsx' ],
        plugins: [ new TsconfigPathsPlugin({ configFile: './src/main/js/tsconfig.json' }) ]
    }
};
