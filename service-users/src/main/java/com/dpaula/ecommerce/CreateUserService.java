package com.dpaula.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

/**
 * @author Fernando de Lima
 */
public class CreateUserService {
    private final Connection connection;

//    private final KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<>();

    CreateUserService() throws SQLException {

        // conexão simples ao banco
        String url = "jdbc:sqlite:users_database.db";
//        String url = "jdbc:sqlite:target/users_database.db";
        this.connection = DriverManager.getConnection(url);

        String sql = " create table Users\n" +
                "                        (\n" +
                "                            uuid varchar(200) primary key,\n" +
                "                            email varchar(200)\n" +
                "                        )";

        try {

            connection.createStatement().execute(sql);
        } catch (SQLException e) {
            //para nao dar erro, pois na segunda vez o banco ja existe
        }
    }

    public static void main(String[] args) throws SQLException {

//        var userService = new CreateUserService();
//
//        try (KafkaService<Order> service = new KafkaService<>(CreateUserService.class.getSimpleName(),
//                "ECOMMERCE_NEW_ORDER",
//                userService::parse,
//                Order.class,
//                Map.of())) {
//
//            service.run();
//        }

        var fraudService = new CreateUserService();

        try (KafkaService<Order> service = new KafkaService<>(CreateUserService.class.getSimpleName()+"5",
                "ECOMMERCE_NEW_ORDER",
                fraudService::parse,
                Order.class,
                Map.of())) {

            service.run();
        }
    }

    /**
     * Corpo da execução da mensagem
     */
    private void parse(ConsumerRecord<String, Order> record) throws SQLException {
        System.out.println("--------------------------------------------------");
        System.out.println("Processando novo pedido, verificando por novo usuário");
        System.out.println("Valor " + record.value());

        final var order = record.value();

        if (ehUsuarioNovo(order.getEmail())) {
            inserirNovoUsuario(order.getEmail());
        }

    }

    private void inserirNovoUsuario(String email) throws SQLException {

        var sql = "insert into Users (uuid, email)" +
                "values (?,?) ";

        final var preparedStatement = connection.prepareStatement(sql);

        var uuid = UUID.randomUUID().toString();

        preparedStatement.setString(1, uuid);
        preparedStatement.setString(2, email);
        preparedStatement.execute();

        System.out.println("Usuário uuid adicionado, email: " + email);
    }

    private boolean ehUsuarioNovo(String email) throws SQLException {

        var sql = "select uuid from Users " +
                " where email = ? limit 1 ";

        final var preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, email);
        final var resultSet = preparedStatement.executeQuery();

        return !resultSet.next();
    }

}
