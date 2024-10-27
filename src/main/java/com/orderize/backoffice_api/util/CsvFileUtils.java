package com.orderize.backoffice_api.util;

import com.orderize.backoffice_api.exception.CsvFileException;
import com.orderize.backoffice_api.model.Attestation;
import com.orderize.backoffice_api.model.Drink;
import com.orderize.backoffice_api.model.Pizza;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CsvFileUtils {

    public static void writeFile(List<Attestation> list) {
        FileWriter arq = null;
        Formatter output = null;
        Boolean gotProblems = false;

        String fileName = "attestations.csv";

        try {
            arq = new FileWriter(fileName);
            output = new Formatter(arq);
        } catch (IOException erro) {
            throw new CsvFileException("Falha ao criar gravar dados no arquivo csv");
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

        try {
            output.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n",
                    "Id",
                    "Id Pedido",
                    "Horario De Criação",
                    "Valor",
                    "Tipo De Pedido",
                    "Nome Cliente",
                    "Telefone Cliente",
                    "Rua Cliente",
                    "Numero Casa Cliente",
                    "Bairro Cliente",
                    "Nome Empresa",
                    "CNPJ Empresa",
                    "Pizzas",
                    "Bebidas"
                    );

            for (Attestation attestation : list) {
                String pizzas = String.join(", ", attestation.getOrder().getPizzas().stream().map(it -> it.toString()).toList());
                String bebidas = String.join(", ", attestation.getOrder().getDrinks().stream().map(it -> it.toString()).toList());
                output.format("%d;%d;%s;%s;%s;%s;%s;%s;%d;%s;%s;%s;%s;%s;\n",
                        attestation.getId(),
                        attestation.getOrder().getId(),
                        attestation.getCreatedTime().format(dateFormatter),
                        numberFormat.format(attestation.getOrder().getPrice()),
                        attestation.getOrder().getType(),
                        attestation.getOrder().getClient().getName(),
                        attestation.getOrder().getClient().getPhone(),
                        attestation.getOrder().getClient().getAddress().getStreet(),
                        attestation.getOrder().getClient().getAddress().getNumber(),
                        attestation.getOrder().getClient().getAddress().getNeighborhood(),
                        attestation.getOrder().getResponsible().getEnterprise().getName(),
                        attestation.getOrder().getResponsible().getEnterprise().getCnpj(),
                        pizzas,
                        bebidas
                        );
            }
        } catch (FormatterClosedException erro) {
            throw new CsvFileException("Falha ao criar ou gravar dados no arquivo csv");
        } finally {
            output.close();
            try {
                arq.close();
            } catch (IOException erro) {
                throw new CsvFileException("Falha ao criar ou gravar dados no arquivo csv");
            }
        }
    }

}
