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
import { message } from 'ant-design-vue'

export default {
	// 对下载的流进行处理，直接从浏览器下载下来
	resultDownload (res) {
		if (res.data.type === 'application/json') {
			// 错误以及无权限
			const reader = new FileReader(res.data)
			reader.readAsText(res.data)
			reader.onload = () => {
				const result = JSON.parse(reader.result)
				message.error(result.msg)
			}
		} else {
			const blob = new Blob([res.data], { type: 'application/octet-stream;charset=UTF-8' })
			const contentDisposition = res.headers['content-disposition']
			const patt = new RegExp('filename=([^;]+\\.[^\\.;]+);*')
			const $link = document.createElement('a')
			$link.href = URL.createObjectURL(blob)
			$link.download = decodeURIComponent(patt.exec(contentDisposition)[1])
			$link.click()
			document.body.appendChild($link)
			document.body.removeChild($link) // 下载完成移除元素
			window.URL.revokeObjectURL($link.href) // 释放掉blob对象
		}
	}
}
