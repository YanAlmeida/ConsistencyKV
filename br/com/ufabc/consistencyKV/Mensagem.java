package br.com.ufabc.consistencyKV;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mensagem {
    /*
     * Classe para representar a mensagem trocada entre os clientes e os servidores
     */
    public String messageType;
    public String sender;
    public String receiver;
    public Integer timestamp;
    public String data;
    public String requestUUID;

    public Mensagem(String type, String origem, String destino, Integer timestampEnvio, String dados) {
        /*
         * Construtor para gerar mensagem a partir dos dados
         * (tipo, sender, destino, timestamp e dados/corpo da mensagem).
         * Gera um UUID aleatório para o request.
         */
        messageType = type;
        sender = origem;
        receiver = destino;
        timestamp = timestampEnvio;
        data = dados;
        requestUUID = UUID.randomUUID().toString();
    }

    public Mensagem(String type, String origem, String destino, Integer timestampEnvio, String dados, String uuid) {
        /*
         * Construtor para gerar mensagem a partir dos dados
         * (tipo, sender, destino, timestamp, dados/corpo da mensagem e UUID do request gerado)
         */
        messageType = type;
        sender = origem;
        receiver = destino;
        timestamp = timestampEnvio;
        data = dados;
        requestUUID = uuid;
    }

    public Mensagem(String jsonString) {
        /*
         * Construtor para gerar mensagem a partir de sua representação como string em
         * JSON
         */
        Pattern typePattern = Pattern.compile("\"messageType\": \"(.+?)\"");
        Pattern senderPattern = Pattern.compile("\"sender\": \"(.+?)\"");
        Pattern receiverPattern = Pattern.compile("\"receiver\": \"(.+?)\"");
        Pattern timestampPattern = Pattern.compile("\"timestamp\": \"(.+?)\"");
        Pattern dataPattern = Pattern.compile("\"data\": \"(.+?)\"");
        Pattern uuidPattern = Pattern.compile("\"requestUUID\": \"(.+?)\"");

        Matcher typeMatcher = typePattern.matcher(jsonString);
        Matcher senderMatcher = senderPattern.matcher(jsonString);
        Matcher receiverMatcher = receiverPattern.matcher(jsonString);
        Matcher timestampMatcher = timestampPattern.matcher(jsonString);
        Matcher dataMatcher = dataPattern.matcher(jsonString);
        Matcher uuidMatcher = uuidPattern.matcher(jsonString);

        typeMatcher.find();
        senderMatcher.find();
        receiverMatcher.find();
        timestampMatcher.find();
        dataMatcher.find();
        uuidMatcher.find();

        messageType = checkNull(typeMatcher.group(1));
        sender = checkNull(senderMatcher.group(1));
        receiver = checkNull(receiverMatcher.group(1));
        timestamp = processInteger(timestampMatcher.group(1));
        data = checkNull(dataMatcher.group(1));
        requestUUID = checkNull(uuidMatcher.group(1));
    }

    public Integer processInteger(String valor) {
        /*
         * Método para processar campos integer do JSON
         */
        if (valor.equals("null")) {
            return null;
        }
        return Integer.parseInt(valor);
    }

    public String checkNull(String valor) {
        /*
         * Método para processar campos retornados como null no JSON
         */
        if (valor.equals("null")) {
            return null;
        }
        return valor;
    }

    public String toJson() {
        /* 
         * Método para transformar a mensagem em string JSON
         */

        return String.format(
                "{\"messageType\": \"%s\", \"sender\": \"%s\", \"receiver\": \"%s\", \"timestamp\": \"%s\", \"data\": \"%s\", \"requestUUID\": \"%s\"}",
                messageType,
                sender,
                receiver,
                timestamp,
                data,
                requestUUID);
    }
}