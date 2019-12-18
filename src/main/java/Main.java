

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;

import javax.activation.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/main")
public class Main extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        MainBase base = new MainBase();
        ResultSet rs = base.get();
        try {
            while(rs.next()) {
                writer.print("<pre>");


                for (int i = 1; i < rs.getMetaData().getColumnCount(); ++i ){
                    writer.print(rs.getString(i));
                    writer.print(" ");
                }




                writer.print("</pre></br>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
