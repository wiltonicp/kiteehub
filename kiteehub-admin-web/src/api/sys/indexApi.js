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

const request = (url, ...arg) => baseRequest(`/sys/index/${url}`, ...arg)
/**
 * 系统首页控制器
 *
 * @author yubaoshan
 * @date 2022-09-22 22:33:20
 */
export default {
	// 添加当前用户日程
	indexScheduleAdd(data) {
		return request('schedule/add', data)
	},
	// 删除日程
	indexScheduleDeleteSchedule(data) {
		return request('schedule/deleteSchedule', data)
	},
	// 获取当前用户日程列表
	indexScheduleList(data) {
		return request('schedule/list', data, 'get')
	},
	// 获取当前用户站内信列表
	indexMessageList(data) {
		return request('message/list', data, 'get')
	},
	// 获取站内信详情
	indexMessageDetail(data) {
		return request('message/detail', data, 'get')
	},
	// 获取当前用户访问日志列表
	indexVisLogList(data) {
		return request('visLog/list', data, 'get')
	},
	// 获取当前用户操作日志列表
	indexOpLogList(data) {
		return request('opLog/list', data, 'get')
	}
}
