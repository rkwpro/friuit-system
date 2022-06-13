function delFruit(fid){
    if(confirm('是否确认删除？')){
        window.location.href='del.do?fid='+fid;
    }
}
function licit(pageNum,pageEnd){
    if(confirm('当前页面非法，是否跳转到合法页面？')){
        window.location.href='del.do?fid=1';
    }
}
function page(pageNum,pageEnd){
    if(pageNum <= 0)
    {
        alert('当前已经在首页了');
    }
    else if(pageNum > pageEnd)
    {
        alert('已经是最后一页了');
    }
    else
    {
        window.location.href='index?pageNo='+pageNum;
    }

}