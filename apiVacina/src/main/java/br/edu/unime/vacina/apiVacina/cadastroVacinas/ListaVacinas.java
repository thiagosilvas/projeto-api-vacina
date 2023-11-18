package br.edu.unime.vacina.apiVacina.cadastroVacinas;

import br.edu.unime.vacina.apiVacina.entity.Vacina;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ListaVacinas {

    public List<Vacina> CadastrarVacinasPredefinidas() {
        List<Vacina> vacinasAdicionarList = new ArrayList<>();

        Vacina vacina1 = new Vacina();
        vacina1.setFabricante("CORONAVAC");
        vacina1.setLote("Lote123");
        vacina1.setDataDeValidade(LocalDate.now().plusMonths(6));
        vacina1.setNumeroDeDoses(2);
        vacina1.setIntervaloDeDoses(21);

        Vacina vacina2 = new Vacina();
        vacina2.setFabricante("PFIZER");
        vacina2.setLote("Lote456");
        vacina2.setDataDeValidade(LocalDate.now().plusMonths(12));
        vacina2.setNumeroDeDoses(1);

        Vacina vacina3 = new Vacina();
        vacina3.setFabricante("JHONSON&JHONSON");
        vacina3.setLote("Lote789");
        vacina3.setDataDeValidade(LocalDate.now().plusMonths(24));
        vacina3.setNumeroDeDoses(0);

        // Adicionando mais 7 exemplos
        Vacina vacina4 = new Vacina();
        vacina4.setFabricante("ASTRAZENECA");
        vacina4.setLote("Lote101");
        vacina4.setDataDeValidade(LocalDate.now().plusMonths(18));
        vacina4.setNumeroDeDoses(2);
        vacina4.setIntervaloDeDoses(28);

        Vacina vacina5 = new Vacina();
        vacina5.setFabricante("SINOVAC");
        vacina5.setLote("Lote202");
        vacina5.setDataDeValidade(LocalDate.now().plusMonths(9));
        vacina5.setNumeroDeDoses(2);
        vacina5.setIntervaloDeDoses(14);

        Vacina vacina6 = new Vacina();
        vacina6.setFabricante("MODERNA");
        vacina6.setLote("Lote303");
        vacina6.setDataDeValidade(LocalDate.now().plusMonths(15));
        vacina6.setNumeroDeDoses(2);
        vacina6.setIntervaloDeDoses(28);

        Vacina vacina7 = new Vacina();
        vacina7.setFabricante("NOVAVAX");
        vacina7.setLote("Lote404");
        vacina7.setDataDeValidade(LocalDate.now().plusMonths(20));
        vacina7.setNumeroDeDoses(1);

        Vacina vacina8 = new Vacina();
        vacina8.setFabricante("COVAXIN");
        vacina8.setLote("Lote505");
        vacina8.setDataDeValidade(LocalDate.now().plusMonths(14));
        vacina8.setNumeroDeDoses(2);
        vacina8.setIntervaloDeDoses(21);

        Vacina vacina9 = new Vacina();
        vacina9.setFabricante("SPUTNIK V");
        vacina9.setLote("Lote606");
        vacina9.setDataDeValidade(LocalDate.now().plusMonths(22));
        vacina9.setNumeroDeDoses(2);
        vacina9.setIntervaloDeDoses(21);

        Vacina vacina10 = new Vacina();
        vacina10.setFabricante("BIONTECH");
        vacina10.setLote("Lote707");
        vacina10.setDataDeValidade(LocalDate.now().plusMonths(10));
        vacina10.setNumeroDeDoses(2);
        vacina10.setIntervaloDeDoses(21);

        vacinasAdicionarList.add(vacina1);
        vacinasAdicionarList.add(vacina2);
        vacinasAdicionarList.add(vacina3);
        vacinasAdicionarList.add(vacina4);
        vacinasAdicionarList.add(vacina5);
        vacinasAdicionarList.add(vacina6);
        vacinasAdicionarList.add(vacina7);
        vacinasAdicionarList.add(vacina8);
        vacinasAdicionarList.add(vacina9);
        vacinasAdicionarList.add(vacina10);

        return vacinasAdicionarList;
    }
}
