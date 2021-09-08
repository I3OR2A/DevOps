import request from '@/utils/request'

export default {
  // 主機列表（条件查询分页）
  // current当前页 limit每页记录数 hostInfoQuery
  getHostListPage(current, limit, hostInfoQuery) {
    return request({
      // url: '/eduservice/teacher/pageTeacherCondition/'+current+"/"+limit,
      url: `/host/host-info/pageHostCondition/${current}/${limit}`,
      method: 'post',
      // teacherQuery条件对象，后端使用RequestBody获取对象
      // data表示把对象转换json进行传递到接口里面
      data: hostInfoQuery
    })
  },
  // 删除主機
  deleteHost(id) {
    return request({
      url: `/host/host-info/deleteHost/${id}`,
      method: 'delete'
    })
  },
  // 添加主機
  addHost(hostInfo) {
    return request({
      url: `/host/host-info/addHost`,
      method: 'post',
      data: hostInfo
    })
  },
  // 根据id查询主機，回显数据
  getHost(id) {
    return request({
      url: `/host/host-info/getHost/${id}`,
      method: 'get'
    })
  },
  // 修改主機
  updateHost(hostInfo) {
    return request({
      url: `/host/host-info/updateHost`,
      method: 'post',
      data: hostInfo
    })
  },
  // 2 查询所有主機
  getListHost() {
    return request({
      url: `/host/host-info/findAll`,
      method: 'get'
    })
  }
}
