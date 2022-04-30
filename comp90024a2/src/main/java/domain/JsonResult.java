package domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * JsonResult(type, data, msg):
 * If the request is successful (JsonResult.type=ture), the requested data is in JsonResult.data,
 * if the request fails (JsonResult.type=ture) JsonResult.msg is an error message
 */
public class JsonResult<T> {
    private boolean type;
    private T data;
    private String msg;
    public static<T> JsonResult success(T data){
        JsonResult js = new JsonResult();
        js.data = data;
        js.type = true;
        return js;
    }
    public static<T> JsonResult error(String msg){
        JsonResult js = new JsonResult();
        js.msg = msg;
        js.type = false;
        return js;
    }
}
