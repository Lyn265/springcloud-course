import Swal from "sweetalert2";
export const Confirm ={
  show(message,callback){
    Swal.fire({
      title: '确认删除?',
      text: message,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: '确认'
    }).then((result) => {
      if(result.isConfirmed){
        if(callback){
          callback();
        }
      }
    })
  }
};
