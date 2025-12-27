package Enums;

public enum MaquinaEstado {

    EN_LISTA("EnLista"),
    EN_REVISION("En revisión"),
    REVISADA("Revisada"),
    CONFIRMADA("Confirmada"),
    LISTA("Lista"),
    RETIRADA("Retirada");

    private final String status;

    MaquinaEstado(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }


}
