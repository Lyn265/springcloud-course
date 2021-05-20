import {Tool} from "./tool";
import {toast} from "./toast";

export const Validator = {
  require: function (value, text) {
    if (Tool.isEmpty(value)) {
      toast.warning(text + "不能为空");
      return false;
    } else {
      return true
    }
  },

  length: function (value, text, min, max) {
    if (Tool.isEmpty(value)) {
      return true;
    }
    if (!Tool.isLength(value, min, max)) {
      toast.warning(text + "长度" + min + "~" + max + "位");
      return false;
    } else {
      return true
    }
  }
};
