/*
 * Copyright [2022] [https://www.kiteehub.com]
 *
 * Kiteehub是内部代码，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Kiteehub源码头部的版权声明。
 * 3.本项目代码使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.kiteehub.com
 * 5.本项目只可用于内部开发，如有问题可联系团队wilton.icp@gmail.com商议合作。
 */
module.exports = {
	// 一行最多 180 字符
	printWidth: 120,
	// 使用 2 个tab缩进
	tabWidth: 2,
	// 使用tab符缩进，false为空格缩进
	useTabs: true,
	// 行尾需要分号
	semi: false,
	// 使用单引号
	singleQuote: true,
	// 对象的 key 仅在必要时用引号
	quoteProps: 'as-needed',
	// jsx 不使用单引号，而使用双引号
	jsxSingleQuote: false,
	// 末尾不需要逗号
	trailingComma: 'none',
	// 大括号内的首尾需要空格
	bracketSpacing: true,
	// jsx 标签的反尖括号不需要换行
	jsxBracketSameLine: true,
	// 箭头函数，只有一个参数的时候，也需要括号
	arrowParens: 'always',
	// 根据显示样式决定 html 要不要折行
	htmlWhitespaceSensitivity: 'css',
	// 换行符使用 lf
	endOfLine: 'lf',
	// 缩进js跟css
	vueIndentScriptAndStyle: true
}
