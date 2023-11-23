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
import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/dev/log/${url}`, ...arg)
/**
 * 日志
 *
 * @author yubaoshan
 * @date 2022-09-22 22:33:20
 */
export default {
	// 获取日志分页
	logPage(data) {
		return request('page', data, 'get')
	},
	// 获取访问日志折线图数据
	logVisLineChartData(data) {
		return request('vis/lineChartData', data, 'get')
	},
	// 获取访问日志饼状图数据
	logVisPieChartData(data) {
		return request('vis/pieChartData', data, 'get')
	},
	// 获取操作日志柱状图数据
	logOpBarChartData(data) {
		return request('op/barChartData', data, 'get')
	},
	// 获取操作日志饼状图数据
	logOpPieChartData(data) {
		return request('op/pieChartData', data, 'get')
	},
	// 清空日志
	logDelete(data) {
		return request('delete', data)
	}
}
