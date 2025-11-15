package modelos.Notificacion;

import java.util.UUID;

public class NovedadItem {
    private String ordenId;
    private String comentario;
    private String id;

    public NovedadItem(String ordenId, String comentario){
        this.ordenId = ordenId;
        this.comentario = comentario;
        this.id = generarNovedadId();
    }


    public String generarNovedadId() {
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String id = "ITNVD-" + uuid;
        return id;
    }

    public String getId(){
        return this.id;
    }


    public String getOrdenId(){
        return this.ordenId;
    }

    public String getComentario(){
        return this.comentario;
    }

    public void setOrdenId(String ordenId){
        this.ordenId = ordenId;
    }

    public void setComentario(String comentario){
        this.comentario = comentario;
    }
}
