module.exports = {
    root: true,
    parser: '@typescript-eslint/parser',
    env: {
        node: true
    },
    parserOptions: {
        ecmaVersion: 2020,
        sourceType: 'module'
    },
    plugins: [
        '@typescript-eslint'
    ],
    extends: [
        '@jitsi/eslint-config'
    ],
    'globals': {
        'NodeJS': true
    },
    'rules': {
        'no-unused-vars': 'off',
        '@typescript-eslint/no-unused-vars': 'error'
    }
};
