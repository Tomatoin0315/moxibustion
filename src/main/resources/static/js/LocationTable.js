layui.use('table', function(){
    var table = layui.table;

    //方法级渲染
    table.render({
        elem: '#LAY_table_user'
        ,type: 'get'
        ,url: '/position/table'
        ,cols: [[
            {checkbox: true, fixed: true}
            ,{field:'id', title: 'ID', width:80, sort: true, fixed: true}
            ,{field:'name', title: '名称', width:80}
            ,{field:'GaiShu', title: '概述',edit:'GaiShu'}
            ,{field:'ZhengZhuang', title: '症状', edit:'ZhengZhuang'}
            ,{field:'BingYin', title: '病因分析与鉴别',edit:'BingYin'}
            ,{field:'QuXue', title: '艾灸取穴',  edit:'QuXue'}
            ,{field:'AnLi', title: '案例',  edit:'AnLi'}
            ,{field:'TiHui', title: '艾灸体会', edit:'TiHui'}
            ,{fixed: 'right', title:'操作', align:'center', toolbar: '#barDemo'}
        ]]
        ,id: 'testReload'
        ,page: true
        ,limit: 10
        ,limits:[10,20,30,40,50]
        ,height: 500
        //以下为静态测试数据
        // ,data:[{
        //     id:3,
        //     name:'恶风',
        //     GaiShu:'概述',
        //     ZhengZhuang:'症状',
        //     BingYin:'病因分析与鉴别',
        //     QuXue:'艾灸取穴',
        //     AnLi:'案例',
        //     TiHui:'艾灸体会',
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
            url:'/position/updateTable',
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