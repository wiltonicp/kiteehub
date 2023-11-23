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
const colorList = [
	{
		key: '薄暮',
		color: '#F5222D'
	},
	{
		key: '火山',
		color: '#FA541C'
	},
	{
		key: '胭脂粉',
		color: '#EB2F96'
	},
	{
		key: '日暮',
		color: '#FAAD14'
	},
	{
		key: '明青',
		color: '#13C2C2'
	},
	{
		key: '极光绿',
		color: '#52C41A'
	},
	{
		key: '深绿',
		color: '#009688'
	},
	{
		key: '拂晓蓝（默认）',
		color: '#1890FF'
	},
	{
		key: '极客蓝',
		color: '#2F54EB'
	},
	{
		key: '酱紫',
		color: '#722ED1'
	},
	{
		key: '主题黑',
		color: '#001529'
	}
]

const updateColorWeak = (colorWeak) => {
	// document.body.className = colorWeak ? 'colorWeak' : '';
	const app = document.body.querySelector('#app')
	colorWeak ? app.classList.add('colorWeak') : app.classList.remove('colorWeak')
}

export { colorList, updateColorWeak }
