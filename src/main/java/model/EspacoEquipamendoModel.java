package model;

public class EspacoEquipamendoModel {
    private Long IdEquipamendoFk;
    private Long IdEspacoFk;

    public Long getIdEquipamendoFk() {
        return IdEquipamendoFk;
    }

    public void setIdEquipamendoFk(Long idEquipamendo) {
        this.IdEquipamendoFk = idEquipamendo;
    }

    public Long getIdEspacoFk() {
        return IdEspacoFk;
    }

    public void setIdEspacoFk(Long idEspaco) {
        this.IdEspacoFk = idEspaco;
    }
}
