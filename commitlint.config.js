module.exports = {
  extends: ['@commitlint/config-conventional'],
  parserPreset: {
    parserOpts: {
      headerPattern: /^([a-z]+)\((.*)\):\s(.+)$/,
      headerCorrespondence: ['type', 'issue', 'subject'],
    },
  },
  plugins: ['commitlint-plugin-function-rules'],
  rules: {
    'subject-case': [0, 'never'],
    'type-enum': [2, 'always', ['feat', 'fix', 'docs', 'style', 'refactor', 'test', 'chore']],
    'function-rules/scope-enum': [
      2, 
      'always',
      (parsed) => {
        const { issue } = parsed;
        if (!issue) return [false, 'issue may not be empty'];
        return [
          /^#[0-9]+$/.test(issue),
          "Issue must be in the format '#number'"
        ];
      }
    ]
  },
};