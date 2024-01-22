<template>
	<xn-form-container :title="record.docName" :width="900" :visible="visible" :destroy-on-close="true" @close="onClose">
		<a-form layout="inline" >
			<a-form-item label="知识处理模型">
				<a-input v-model:value="record.fileModel" placeholder="知识处理模型" disabled />
			</a-form-item>
			<a-form-item label="知识类型">
				<a-input v-model:value="record.docType" placeholder="知识类型" disabled />
			</a-form-item>
			<a-form-item label="最后修改时间">
				<a-input v-model:value="record.updateTime" placeholder="最后修改时间" disabled />
			</a-form-item>
			<a-form-item label="索引模型">
				<a-input v-model:value="record.indexModel" placeholder="索引模型" disabled />
			</a-form-item>
		</a-form>
		<a-alert
			:message="`分段预览：（${totalVal} 组）`"
			type="success"
			show-icon
			style="margin: 10px 0"
		>
			<template #icon><smile-outlined /></template>
		</a-alert>
		<div style="background-color: #ececec5f; padding: 10px">
			<a-row :gutter="[18, 18]">
				<a-col :span="8" v-for="(item, index) in recordsList" :key="index">
					<a-card :title="'ID:' + item.id" :bordered="false" :hoverable="true">
						<template #extra>
							<a href="#">
								<a-tag color="blue">{{ item.sorted }}#</a-tag>
							</a>
						</template>
						<template #actions>
							<div>
								<FontSizeOutlined />
								<span style="margin-left: 10px">{{ item.content.length }}</span>
							</div>
							<DeleteOutlined @click="del(item)" />
						</template>
						<p class="chunk">{{ item.content }}</p>
					</a-card>
				</a-col>
			</a-row>
		</div>

		<template #footer>
			<div class="pagination">
				<a-pagination
					v-model:current="current"
					v-model:pageSize="size"
					show-size-changer
					:total="totalVal"
					@showSizeChange="onShowSizeChange"
				/>
			</div>
		</template>
	</xn-form-container>
</template>

<script setup name="knowledgeAttachForm">
import { ExclamationCircleOutlined } from '@ant-design/icons-vue'
import { Modal } from 'ant-design-vue'
import { createVNode } from 'vue'
import tool from '@/utils/tool'
import { cloneDeep } from 'lodash-es'
import { required } from '@/utils/formRules'
import knowledgeAttachApi from '@/api/knowledge/knowledgeAttachApi'
const size = ref(10)
const current = ref(1)
const totalVal = ref(0)
const recordsList = ref([])
const open = ref(false)
let dataObj = reactive({
	record: {}
})

const onShowSizeChange = (currentVal, pageSize) => {
	console.log(currentVal, pageSize)
	size.value = pageSize
	current.value = currentVal
}
watch(size, () => {
	console.log('size', size.value)
	knowledAttachChunk()
})
watch(current, () => {
	console.log('current', current.value)
	knowledAttachChunk()
})
// 抽屉状态
const visible = ref(false)
const emit = defineEmits({ successful: null })

// 打开抽屉
const onOpen = (record) => {
	console.log('打开抽屉', record)
	dataObj.record = record
	visible.value = true
	knowledAttachChunk()
}
// 关闭抽屉
const onClose = () => {
	visible.value = false
}

const knowledAttachChunk = async () => {
	let parame = {
		id: dataObj.record.id,
		current: current.value,
		size: size.value
	}
	let { total, records } = await knowledgeAttachApi.knowledAttachChunk(parame)
	totalVal.value = total
	recordsList.value = records
}

// 删除某一项
const del = (item) => {
	Modal.confirm({
		title: '提示',
		icon: createVNode(ExclamationCircleOutlined),
		content: `是否删除ID为：${item.id}的文本？`,
		okText: '确认',
		cancelText: '取消',
		async onOk() {
			await knowledgeAttachApi.knowledAttachDelete([
				{
					id: item.id
				}
			])
			knowledAttachChunk()
		}
	})
}

const { record } = toRefs(dataObj)

// 抛出函数
defineExpose({
	onOpen
})
</script>

<style  scoped>
.chunk {
	height: 130px;
	overflow: auto;
}
*::-webkit-scrollbar {
	/*滚动条整体样式*/
	width: 6px; /*高宽分别对应横竖滚动条的尺寸*/
	height: 1px;
}
*::-webkit-scrollbar-thumb {
	/*滚动条里面深色条*/
	border-radius: 10px;
	box-shadow: inset 0 0 5px rgba(236, 236, 236, 0.1);
	background: #ccc;
}
*::-webkit-scrollbar-track {
	/*滚动条里面轨道*/
	box-shadow: inset 0 0 5px rgba(236, 236, 236, 0.1);
	border-radius: 10px;
	background: #ededed;
}
</style>

