$(function () {
    layui.use(['tree', 'util','layer','jquery'], function(){
        var tree = layui.tree
            ,layer = layui.layer
            ,util = layui.util;

        //模拟数据  你看这个格式
        // var data = [{
        //     title: '内科'
        //     , id: 1
        //     // ,checked: true
        //     , spread: true
        //     , children: [{
        //         title: '全身症状'
        //         , id: 2
        //         ,children:[
        //             {
        //                 title:'恶风',
        //                 id:3,
        //             },{
        //                 title:'恶寒',
        //                 id:3,
        //             },
        //         ]
        //     },{
        //         title: '四肢症状'
        //         , id: 3
        //
        //     }]
        // }];


        var data;
        //拿初始数据，测试请恢复注释
        $.ajax({
            type:'get',
            url:'/branch/branchCategory/tree',
            success:function (datas) {
                //console.log(datas);
                if (datas.status=='200'){
                    data=datas.data;

                    tree.render({
                        elem: '#test12'
                        ,data: data
                        ,showCheckbox: true  //是否显示复选框
                        ,id: 'demoId1'
                        ,isJump: true //是否允许点击节点时弹出新窗口跳转
                        /*,click: function(obj){
                            var data = obj.data;  //获取当前点击的节点数据
                            layer.msg('状态：'+ obj.state + '<br>节点数据：' + JSON.stringify(data));
                        }*/
                    });


                    //开启节点操作图标
                    tree.render({
                        elem: '#test12'
                        ,data: data
                        ,edit: ['add', 'update', 'del'] //操作节点的图标
                        ,click: function(obj){
                            layer.msg(JSON.stringify(obj.data));
                            console.log(obj.data);
                            console.log(666)
                        },
                        operate: function(obj){
                            var type = obj.type; //得到操作类型：add、edit、del
                            var data = obj.data; //得到当前节点的数据
                            var elem = obj.elem; //得到当前节点元素

                            //Ajax 操作
                            var id = data.id; //得到节点索引
                            var title=data.title;//节点名称
                            console.log(type+data.title+elem+id);
                            if(type === 'add'){ //增加节点
                                //增加的post请求
                                $.ajax({
                                    type:'post',
                                    url:'/branch/add',
                                    data:{
                                        id:id
                                    },
                                    success:function (data) {
                                        if (data.status == '200') {
                                            layer.msg("添加成功")
                                        } else {
                                            layer.msg("添加失败")
                                        }
                                    }
                                });
                            } else if(type === 'update'){ //修改节点
                                //修改的post请求
                                $.ajax({
                                    type:'post',
                                    url:'/branch/update',
                                    data:{
                                        id:id,
                                        value:title
                                    },
                                    success:function (data) {
                                        if (data.status == '200') {
                                            layer.msg("编辑成功")
                                        } else {
                                            layer.msg("编辑失败")
                                        }
                                    }
                                });
                                console.log(elem.find('.layui-tree-txt').html()); //得到修改后的内容
                            } else if(type === 'del'){ //删除节点
                                //删除的post请求
                                $.ajax({
                                    type:'post',
                                    url:'/branch/delete',
                                    data:{
                                        id:id
                                    },
                                    success:function (data) {
                                        if (data.status == '200') {
                                            layer.msg("删除成功")
                                        } else {
                                            layer.msg("删除失败")
                                            tree.reload("demoId1")
                                        }
                                    }
                                });

                            };
                        }
                    });
                }else {
                    layer.msg("error")
                }
            }
        });
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


