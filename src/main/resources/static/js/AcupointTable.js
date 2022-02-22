layui.use('table', function(res){
    var table = layui.table;

    //方法级渲染
    table.render({
        elem: '#LAY_table_user'
        ,type: 'get'
        ,url: '/xuewei/table'
        ,cols: [[
            {checkbox: true, fixed: true}
            ,{field:'id', title: 'ID', width:80, sort: true, fixed: true}
            ,{field:'name', title: '名称', width:80}
            ,{field:'JingMai', title: '所属经脉',edit:'JingMai'}
            ,{field:'code', title: '国际代码', edit:'code',width:80}
            ,{field:'TeDingXue', title: '特定穴',edit:'TeDingXue'}
            ,{field:'DingWei', title: '定位',  edit:'DingWei'}
            ,{field:'QuXueFa', title: '取穴法',  edit:'QuXueFa'}
            ,{field:'ZhuZhiBingZheng', title: '主治病症', edit:'ZhuZhiBingZheng'}
            ,{field:'CanShu', title: '艾灸参数', edit:'CanShu'}
            ,{field:'JingYan', title: '经验应用',  edit:'JingYan'},
            {fixed: 'right', title:'操作', align:'center', toolbar: '#barDemo'}
        ]]
        ,id: 'testReload'
        ,page: true
        ,limit:10
        ,limits:[10,20,30,40,50]
        /*,response:{
            count:"count"
        }*/
        /*,parseData:function (res) {
            return{
                "code": res.data.code,
                "count":res.data.count
            };
        }*/
        ,height: 500
        //以下为静态测试数据
        // ,data:[{
        //     id:3,
        //     name:'养老穴',
        //     JingMai:'手太阳小肠经',
        //     code:'SI6',
        //     TeDingXue:'XJJX',
        //     DingWei:'',
        //     QuXueFa:'',
        //     ZhuZhiBingZheng:'',
        //     CanShu:'',
        //     JingYan:''
        // }]
    });

    //监听编辑
    table.on('edit(user)', function(obj){
        var value = obj.value //得到修改后的值
            ,data = obj.data //得到所在行所有键值
            ,field = obj.field; //得到字段
        //提交编辑修改，测试请放开注释
        $.ajax({
            type: 'post',
            data: {
                id: data.id,
                value:value,
                field:field
            },
            url:'/xuewei/updateTable',
            success:function (data) {
                if (data.status=='200'){
                    layer.msg('[ID: '+ data.id +'] ' + field + ' 字段更改为：'+ value);
                }else {
                    layer.msg("修改失败");
                }
            }
        });

    });

    //监听增加和删除
    table.on('tool(user)', function(obj){
        var data = obj.data;
        console.log(data);
        if(obj.event === 'detail'){
                layer.open({
                    type: 1,
                    content: $('#form_edit'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    area:['40%','500px']
                });
            // layer.msg('ID：'+ data.id + ' 的查看操作');
        } else if(obj.event === 'del'){
            layer.confirm('真的删除行么', function(index){
                obj.del();
                console.log(index);
                layer.close(index);
                //删除数据,测试请恢复
                $.ajax({
                    url: '/deleteTable',
                    type: "post",
                    data:{
                        id:data.id
                    },
                    success:function (data) {
                        if (data.status == '200') {
                            layer.msg(data.msg)
                        } else {
                            layer.msg(data.msg)
                        }
                    }
                });
            });
        }
    });



    //搜索表格重载
    var $ = layui.$, active = {
        reload: function(){
            var demoReload = $('#demoReload');

            //执行重载
            table.reload('testReload', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                    name: demoReload.val()
                }
            }, 'data');
        }
    };

    $('.demoTable .layui-btn').on('click', function(){
        var type = $(this).data('type');
        console.log("重载了");
        active[type] ? active[type].call(this) : '';
    });
});




layui.use('element', function(){
    var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块

    //监听导航点击
    element.on('nav(demo)', function(elem){
        //console.log(elem)
        layer.msg(elem.text());
    });
});