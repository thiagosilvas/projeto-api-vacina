package br.edu.unime.paciente.apiPaciente.testing;

import br.edu.unime.paciente.apiPaciente.entity.Endereco;
import br.edu.unime.paciente.apiPaciente.entity.Paciente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListaPaciente {


    public List<Paciente> ListaPaciente(){

        List<Paciente> pacientesAAdicionar = new ArrayList<>();

        Paciente paciente1 = new Paciente();

        paciente1.setNome("Antonio Vitor");
        paciente1.setSobrenome("Guimaraes");
        paciente1.setDataDeNascimento(LocalDate.of(2000, 9, 9));
        paciente1.setCpf("04445303550");
        paciente1.setContatos(Collections.singletonList("92685988"));
        paciente1.setGenero("Masculino");

        Endereco endereco = new Endereco();
        endereco.setLogradouro("Vila Alto de Amaralina");
        endereco.setCep("41905586");
        endereco.setNumero(10);
        endereco.setBairro( "Nordeste");
        endereco.setMunicipio("Salvador");
        endereco.setEstado("BA");

        paciente1.setEnderecos(Collections.singletonList(endereco));


        Paciente paciente2 = new Paciente();
        paciente2.setNome("Carol");
        paciente2.setSobrenome("Guimaraes");
        paciente2.setDataDeNascimento(LocalDate.of(1999, 4, 12));
        paciente2.setCpf("68988363078");
        paciente2.setContatos(Collections.singletonList("23372466"));
        paciente2.setGenero("Feminino");

        Endereco endereco2 = new Endereco();
        endereco2.setLogradouro("Rua A");
        endereco2.setCep("45608180");
        endereco2.setNumero(10);
        endereco2.setBairro( "Vila Anália");
        endereco2.setMunicipio("Itabuna");
        endereco2.setEstado("BA");

        paciente2.setEnderecos(Collections.singletonList(endereco2));

        Paciente paciente3 = new Paciente();

        paciente3.setNome("Jackson");
        paciente3.setSobrenome("Luis");
        paciente3.setDataDeNascimento(LocalDate.of(1998, 5, 20));
        paciente3.setCpf("921.365.010-89");
        paciente3.setContatos(Collections.singletonList("73971665503"));
        paciente3.setGenero("Masculino");

        Endereco endereco3 = new Endereco();
        endereco3.setLogradouro("Rua Nadima Bagdade Damha");
        endereco3.setCep("79046110");
        endereco3.setNumero(14);
        endereco3.setBairro( "Residencial Damha");
        endereco3.setMunicipio("Campo Grande");
        endereco3.setEstado("MS");

        paciente3.setEnderecos(Collections.singletonList(endereco3));

        Paciente paciente4 = new Paciente();
        paciente4.setNome("Thiago");
        paciente4.setSobrenome("Luis");
        paciente4.setDataDeNascimento(LocalDate.of(1999, 2, 1));
        paciente4.setCpf("474.035.950-25");
        paciente4.setContatos(Collections.singletonList("83992841483"));
        paciente4.setGenero("Masculino");

        Endereco endereco4 = new Endereco();
        endereco4.setLogradouro("Rua Caminho dos Estudantes");
        endereco4.setCep("88047366");
        endereco4.setNumero(122);
        endereco4.setBairro( "Costeira do Pirajubaé");
        endereco4.setMunicipio("Florianópolis");
        endereco4.setEstado("SC");

        paciente4.setEnderecos(Collections.singletonList(endereco4));

        Paciente paciente5 = new Paciente();

        paciente5.setNome("Joseph");
        paciente5.setSobrenome("Oliveira");
        paciente5.setDataDeNascimento(LocalDate.of(1950, 2, 3));
        paciente5.setCpf("63284260030");
        paciente5.setContatos(Collections.singletonList("77982312346"));
        paciente5.setGenero("Masculino");

        Endereco endereco5 = new Endereco();
        endereco5.setLogradouro("Quadra QR 831 Conjunto 7");
        endereco5.setCep("72338717");
        endereco5.setNumero(22);
        endereco5.setBairro( "Samambaia Norte (Samambaia)");
        endereco5.setMunicipio("Brasília");
        endereco5.setEstado("DF");

        paciente5.setEnderecos(Collections.singletonList(endereco5));

        Paciente paciente6 = new Paciente();
        paciente6.setNome("Amanda");
        paciente6.setSobrenome("Yano");
        paciente6.setDataDeNascimento(LocalDate.of(1960, 2, 12));
        paciente6.setCpf("68988363078");
        paciente6.setContatos(Collections.singletonList("amanda2383@gmail.com"));
        paciente6.setGenero("Feminino");

        Endereco endereco6 = new Endereco();
        endereco6.setLogradouro("Quadra QR 831 Conjunto 7");
        endereco6.setCep("72338717");
        endereco6.setNumero(22);
        endereco6.setBairro( "Samambaia Norte (Samambaia)");
        endereco6.setMunicipio("Brasília");
        endereco6.setEstado("DF");

        paciente6.setEnderecos(Collections.singletonList(endereco6));

        pacientesAAdicionar.add(paciente1);
        pacientesAAdicionar.add(paciente2);
        pacientesAAdicionar.add(paciente3);
        pacientesAAdicionar.add(paciente4);
        pacientesAAdicionar.add(paciente5);
        pacientesAAdicionar.add(paciente6);

        return pacientesAAdicionar;
    }


}
