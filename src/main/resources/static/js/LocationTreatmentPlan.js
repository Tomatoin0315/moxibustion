layui.use('table', function(){
    var table = layui.table;

    //方法级渲染
    table.render({
        elem: '#LAY_table_user'
        ,type: 'get'
        ,url: '/position/treatmentPlanTable'
        ,cols: [[
            {checkbox: true, fixed: true}
            ,{field:'id', title: 'ID', width:80, sort: true, fixed: true}
            ,{field:'people', title: '发布人', width:80}
            ,{field:'name', title: '名称', width:80}
            ,{field:'GaiShu', title: '概述',edit:'GaiShu'}
            ,{field:'ZhengZhuang', title: '症状', edit:'ZhengZhuang'}
            ,{field:'BingYin', title: '病因分析与鉴别',edit:'BingYin'}
            ,{field:'QuXue', title: '艾灸取穴',  edit:'QuXue'}
            ,{field:'AnLi', title: '案例',  edit:'AnLi'}
            ,{field:'TiHui', title: '艾灸体会', edit:'TiHui'}
            ,{fixed: 'right', title:'操作',width:130, align:'center', toolbar: '#barDemo'}
        ]]
        ,id: 'testReload'
        ,page: true
        ,limit: 50
        ,height: 500
        //以下为静态测试数据
        // ,data:[{
        //     id:1,
        //     people:yjy,
        //     name:'腰痛'
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
            url:'',
            success:function (data) {
                if (data.status=='200'){
                    layer.msg('[ID: '+ data.id +'] ' + field + ' 字段更改为：'+ value);
                }else {
                    layer.msg("修改失败");
                }
            }
        });

    });

    //监听采用和删除
    table.on('tool(user)', function(obj){
        var data = obj.data;
        console.log(data);
        if(obj.event === 'adopt'){
            //还需要修改，未测试
            //通过即为删除
            obj.del();
            console.log(index);
            layer.close(index);
            //删除数据,测试请恢复
            $.ajax({
                url: '/deleteTreatmentPlanTable',
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
        } else if(obj.event === 'del'){
            layer.confirm('真的删除行么', function(index){
                obj.del();
                console.log(index);
                layer.close(index);
                //删除数据,测试请恢复
                $.ajax({
                    url: '/deleteTreatmentPlanTable',
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