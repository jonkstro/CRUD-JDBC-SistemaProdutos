import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import db.DB;
import db.DbException;

public class App {
    public static void main(String[] args) throws Exception {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        Scanner sc = new Scanner(System.in);
        try {
            conn = DB.gConnection();
            st = conn.createStatement();
            createTable(st);

            System.out.println("SISTEMA DE CADASTRO DE PRODUTOS");
            int op = 0;
            do {
                System.out.println("ESCOLHA O QUE DESEJA:");
                System.out.println("1 - CADASTRAR PRODUTO:");
                System.out.println("2 - LISTAR PRODUTOS:");
                System.out.println("3 - ATUALIZAR PRODUTO:");
                System.out.println("4 - DELETAR PRODUTO:");
                System.out.println("0 - SAIR");
                op = sc.nextInt();
                sc.nextLine();
                switch (op) {
                    case 1:
                        insertSql(st, sc);
                        break;
                    case 2:
                        listSql(rs, st);
                        break;
                    case 3:
                        updateSql(st, rs, sc);
                        break;
                    case 4:
                        deleteSql(st, rs, sc);
                        break;
                    default:
                        break;
                }
            } while (op != 0);

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
            sc.close();
        }
    }

    private static void deleteSql(Statement st, ResultSet rs, Scanner sc) throws SQLException {
        System.out.println("Selecione o item que deseja apagar: ");
        listSql(rs, st);
        int id = sc.nextInt();
        sc.nextLine();
        String sqlDelete = "DELETE FROM tb_produtos WHERE id = " + id;
        st.executeUpdate(sqlDelete);
    }

    private static void updateSql(Statement st, ResultSet rs, Scanner sc) throws SQLException {
        System.out.println("Selecione um item para atualizar: ");
        listSql(rs, st);
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Digite o nome do produto: ");
        String nome = sc.nextLine();
        System.out.print("Digite a descrição do produto: ");
        String descricao = sc.nextLine();
        System.out.print("Digite o preco do produto: ");
        Double preco = sc.nextDouble();
        sc.nextLine();
        System.out.print("Digite o estoque do produto: ");
        Integer estoque = sc.nextInt();
        sc.nextLine();
        String sqlUpdate = "UPDATE tb_produtos SET " +
                "nome = '" + nome + "', " +
                "descricao = '" + descricao + "', " +
                "preco = " + preco + ", " +
                "estoque = " + estoque + " " +
                "WHERE id =" + id;
        st.executeUpdate(sqlUpdate);
        System.out.println("Atualizado com sucesso!!!");
        System.out.println();
    }

    private static void listSql(ResultSet rs, Statement st) throws SQLException {
        rs = st.executeQuery("SELECT * FROM tb_produtos");
        System.out.println();
        System.out.println("ID - Produto");
        while (rs.next()) {
            System.out.println(rs.getInt("id") + " - " + rs.getString("nome"));
        }
        System.out.println();
    }

    private static void insertSql(Statement st, Scanner sc) throws SQLException {
        System.out.println();
        System.out.print("Digite o nome do produto: ");
        String nome = sc.nextLine();
        System.out.print("Digite a descrição do produto: ");
        String descricao = sc.nextLine();
        System.out.print("Digite o preco do produto: ");
        Double preco = sc.nextDouble();
        sc.nextLine();
        System.out.print("Digite o estoque do produto: ");
        Integer estoque = sc.nextInt();
        sc.nextLine();

        String sqlInsert = "INSERT INTO tb_produtos ( " +
                "nome, descricao, preco, estoque) VALUES (" +
                "'" + nome + "', " +
                "'" + descricao + "', " +
                preco + ", " +
                estoque + "" +
                ");";
        st.executeUpdate(sqlInsert);
        System.out.println("Cadastrado com sucesso!");
        System.out.println();
    }

    private static void createTable(Statement st) throws SQLException {
        String sqlCreate = "CREATE TABLE IF NOT EXISTS tb_produtos ( " +
                "id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "nome VARCHAR(30) NOT NULL, " +
                "descricao VARCHAR(30) NOT NULL, " +
                "preco DOUBLE NOT NULL, " +
                "estoque INTEGER NOT NULL" +
                ");";
        st.execute(sqlCreate);
    }
}
