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
function kebabCase(value) {
	return value
		.replace(/([A-Z])/g, ' $1')
		.trim()
		.split(' ')
		.join('-')
		.toLowerCase()
}
const antComponents = [
	'Affix',
	'Alert',
	'Anchor',
	'AnchorLink',
	'AutoComplete',
	'AutoCompleteOptGroup',
	'AutoCompleteOption',
	'Avatar',
	'AvatarGroup',
	'BackTop',
	'Badge',
	'BadgeRibbon',
	'Breadcrumb',
	'BreadcrumbItem',
	'BreadcrumbSeparator',
	'Button',
	'ButtonGroup',
	'Calendar',
	'Card',
	'CardGrid',
	'CardMeta',
	'Carousel',
	'Cascader',
	'CheckableTag',
	'Checkbox',
	'CheckboxGroup',
	'Col',
	'Collapse',
	'CollapsePanel',
	'Comment',
	'ConfigProvider',
	'DatePicker',
	'Descriptions',
	'DescriptionsItem',
	'DirectoryTree',
	'Divider',
	'Drawer',
	'Dropdown',
	'DropdownButton',
	'Empty',
	'Form',
	'FormItem',
	'FormItemRest',
	'Image',
	'ImagePreviewGroup',
	'Input',
	'InputGroup',
	'InputNumber',
	'InputPassword',
	'InputSearch',
	'Layout',
	'LayoutContent',
	'LayoutFooter',
	'LayoutHeader',
	'LayoutSider',
	'List',
	'ListItem',
	'ListItemMeta',
	'LocaleProvider',
	'Mentions',
	'MentionsOption',
	'Menu',
	'MenuDivider',
	'MenuItem',
	'MenuItemGroup',
	'Modal',
	'MonthPicker',
	'PageHeader',
	'Pagination',
	'Popconfirm',
	'Popover',
	'Progress',
	'QuarterPicker',
	'Radio',
	'RadioButton',
	'RadioGroup',
	'RangePicker',
	'Rate',
	'Result',
	'Row',
	'Select',
	'SelectOptGroup',
	'SelectOption',
	'Skeleton',
	'SkeletonAvatar',
	'SkeletonButton',
	'SkeletonImage',
	'SkeletonInput',
	'Slider',
	'Space',
	'Spin',
	'Statistic',
	'StatisticCountdown',
	'Step',
	'Steps',
	'SubMenu',
	'Switch',
	'TabPane',
	'Table',
	'TableColumn',
	'TableColumnGroup',
	'TableSummary',
	'TableSummaryCell',
	'TableSummaryRow',
	'Tabs',
	'Tag',
	'Textarea',
	'TimePicker',
	'TimeRangePicker',
	'Timeline',
	'TimelineItem',
	'Tooltip',
	'Transfer',
	'Tree',
	'TreeNode',
	'TreeSelect',
	'TreeSelectNode',
	'Typography',
	'TypographyLink',
	'TypographyParagraph',
	'TypographyText',
	'TypographyTitle',
	'Upload',
	'UploadDragger',
	'WeekPicker'
]
const matchComponents = [
	{
		pattern: /^Avatar/,
		styleDir: 'avatar'
	},
	{
		pattern: /^AutoComplete/,
		styleDir: 'auto-complete'
	},
	{
		pattern: /^Anchor/,
		styleDir: 'anchor'
	},
	{
		pattern: /^Badge/,
		styleDir: 'badge'
	},
	{
		pattern: /^Breadcrumb/,
		styleDir: 'breadcrumb'
	},
	{
		pattern: /^Button/,
		styleDir: 'button'
	},
	{
		pattern: /^Checkbox/,
		styleDir: 'checkbox'
	},
	{
		pattern: /^Card/,
		styleDir: 'card'
	},
	{
		pattern: /^Collapse/,
		styleDir: 'collapse'
	},
	{
		pattern: /^Descriptions/,
		styleDir: 'descriptions'
	},
	{
		pattern: /^RangePicker|^WeekPicker|^MonthPicker|^QuarterPicker/,
		styleDir: 'date-picker'
	},
	{
		pattern: /^TimeRangePicker/,
		styleDir: 'time-picker'
	},
	{
		pattern: /^Dropdown/,
		styleDir: 'dropdown'
	},
	{
		pattern: /^Form/,
		styleDir: 'form'
	},
	{
		pattern: /^InputNumber/,
		styleDir: 'input-number'
	},
	{
		pattern: /^Input|^Textarea/,
		styleDir: 'input'
	},
	{
		pattern: /^Statistic/,
		styleDir: 'statistic'
	},
	{
		pattern: /^CheckableTag/,
		styleDir: 'tag'
	},
	{
		pattern: /^Layout/,
		styleDir: 'layout'
	},
	{
		pattern: /^Menu|^SubMenu/,
		styleDir: 'menu'
	},
	{
		pattern: /^Table/,
		styleDir: 'table'
	},
	{
		pattern: /^Radio/,
		styleDir: 'radio'
	},
	{
		pattern: /^Image/,
		styleDir: 'image'
	},
	{
		pattern: /^List/,
		styleDir: 'list'
	},
	{
		pattern: /^Tab/,
		styleDir: 'tabs'
	},
	{
		pattern: /^Mentions/,
		styleDir: 'mentions'
	},
	{
		pattern: /^Step/,
		styleDir: 'steps'
	},
	{
		pattern: /^Skeleton/,
		styleDir: 'skeleton'
	},
	{
		pattern: /^Select/,
		styleDir: 'select'
	},
	{
		pattern: /^TreeSelect/,
		styleDir: 'tree-select'
	},
	{
		pattern: /^Tree|^DirectoryTree/,
		styleDir: 'tree'
	},
	{
		pattern: /^Typography/,
		styleDir: 'typography'
	},
	{
		pattern: /^Timeline/,
		styleDir: 'timeline'
	},
	{
		pattern: /^Upload/,
		styleDir: 'upload'
	}
]
const antStyleDeps = Array.from(
	new Set(
		antComponents.map((name) => {
			for (let i = 0; i < matchComponents.length; i++) {
				if (name.match(matchComponents[i].pattern)) {
					return matchComponents[i].styleDir
				}
			}
			return kebabCase(name)
		})
	)
).map((name) => {
	return `ant-design-vue/es/${name}/style`
})

export default antStyleDeps
