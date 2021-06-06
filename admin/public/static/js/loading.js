Loading = {
  show(){
    $.blockUI({
      message:'<img src="/static/image/loading.gif">',
      css:{
        zIndex:"1011",
        padding:"10px",
        left:'50%',
        width:"80px",
        marginLeft:"-40px"
      }
    })
  },
  hide(){
    $.unblockUI();
  }
};
