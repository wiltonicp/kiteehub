<template>
  <xn-form-container
      :title="formData.id ? '编辑' : '导入'"
      :width="700"
      :visible="visible"
      :destroy-on-close="true"
      @close="onClose"
  >
    <a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
      <a-form-item label="补贴类型" name="subsidyType">
        <a-select v-model:value="formData.subsidyType" placeholder="请选择补贴类型" :options="subsidyTypeOptions" />
      </a-form-item>

      <span>导入数据格式严格按照系统模板进行数据录入，请点击
			  <a-button type="primary" size="small" @click="downloadImportSubsidyTemplate">下载模板</a-button>
		  </span>
      <a-divider dashed />
      <div>
        <a-spin :spinning="impUploadLoading">
          <a-upload-dragger :show-upload-list="false" :custom-request="customRequestLocal" :accept="uploadAccept">
            <p class="ant-upload-drag-icon">
              <inbox-outlined></inbox-outlined>
            </p>
            <p class="ant-upload-text">单击或拖动文件到此区域进行上传</p>
            <p class="ant-upload-hint">仅支持xls、xlsx格式文件</p>
          </a-upload-dragger>
        </a-spin>
      </div>
    </a-form>
  </xn-form-container>
</template>

<script setup name="kbSubsidyBatchForm">
import { message } from 'ant-design-vue'
import tool from '@/utils/tool'
import { cloneDeep } from 'lodash-es'
import { required } from '@/utils/formRules'
import kbSubsidyBatchApi from '@/api/knowledge/kbSubsidyBatchApi'
import downloadUtil from '@/utils/downloadUtil'
// 抽屉状态
const visible = ref(false)
const emit = defineEmits({ successful: null })
const formRef = ref()
// 表单数据
const formData = ref({})
const submitLoading = ref(false)
const subsidyTypeOptions = ref([])

const impUploadLoading = ref(false)
const impAlertStatus = ref(false)
const impResultData = ref({})
const impResultErrorDataSource = ref([])
const impAccept = [
  {
    extension: '.xls',
    mimeType: 'application/vnd.ms-excel'
  },
  {
    extension: '.xlsx',
    mimeType: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
  }
]
// 指定能选择的文件类型
const uploadAccept = String(
    impAccept.map((item) => {
      return item.mimeType
    })
)

// 打开抽屉
const onOpen = (record) => {
  visible.value = true
  if (record) {
    let recordData = cloneDeep(record)
    formData.value = Object.assign({}, recordData)
  }
  subsidyTypeOptions.value = tool.dictList('SUBSIDY_TYPE')
}
// 关闭导入提示
const onImpClose = () => {
  impAlertStatus.value = false
}
// 关闭抽屉
const onClose = () => {
  formRef.value.resetFields()
  formData.value = {}
  visible.value = false
  onImpClose()
}
// 默认要校验的
const formRules = {
  subsidyType: [required('请选择补贴类型')],
}

// 导入
const customRequestLocal = (data) => {
  formRef.value.validate().then(() => {
    impUploadLoading.value = true
    // 校验上传文件扩展名和文件类型是否为.xls、.xlsx
    const extension = '.'.concat(data.file.name.split('.').slice(-1).toString().toLowerCase())
    const mimeType = data.file.type
    // 提取允许的扩展名
    const extensionArr = impAccept.map((item) => item.extension)
    // 提取允许的MIMEType
    const mimeTypeArr = impAccept.map((item) => item.mimeType)
    if (!extensionArr.includes(extension) || !mimeTypeArr.includes(mimeType)) {
      message.warning('上传文件类型仅支持xls、xlsx格式文件！')
      impUploadLoading.value = false
      return false
    }
	  formData.file = data.file
    return onSubmit(data)
  })
}

// 验证并提交数据
const onSubmit = (data) => {
  formRef.value.validate().then(() => {
    submitLoading.value = true
	  const fileData = new FormData()
	  fileData.append('file', data.file)
	  fileData.append('subsidyType',formData.value.subsidyType)
    kbSubsidyBatchApi
        .kbSubsidyBatchImport(fileData)
        .then((res) => {
          impAlertStatus.value = true
          impResultData.value = res
          impResultErrorDataSource.value = res.errorDetail
			message.success('导入成功！')
          emit('successful')
			onClose()
        })
        .finally(() => {
          submitLoading.value = false
        })
  })
}
// 下载导入模板
const downloadImportSubsidyTemplate = () => {
  kbSubsidyBatchApi.downloadImportSubsidyTemplate().then((res) => {
    downloadUtil.resultDownload(res)
  })
}
// 抛出函数
defineExpose({
  onOpen
})
</script>
