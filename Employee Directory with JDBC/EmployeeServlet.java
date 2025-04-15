import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
    private String dbURL, dbUser, dbPassword;

    public void init() {
        try (InputStream input = getServletContext().getResourceAsStream("/WEB-INF/db-config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            dbURL = prop.getProperty("db.url");
            dbUser = prop.getProperty("db.user");
            dbPassword = prop.getProperty("db.password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h2>Employee Information</h2>");

        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword)) {
            PreparedStatement stmt;
            if (idParam != null && !idParam.isEmpty()) {
                stmt = conn.prepareStatement("SELECT * FROM employees WHERE id = ?");
                stmt.setInt(1, Integer.parseInt(idParam));
            } else {
                stmt = conn.prepareStatement("SELECT * FROM employees");
            }

            ResultSet rs = stmt.executeQuery();
            out.println("<table border='1'><tr><th>ID</th><th>Name</th><th>Department</th><th>Email</th></tr>");

            boolean found = false;
            while (rs.next()) {
                found = true;
                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("department") + "</td>");
                out.println("<td>" + rs.getString("email") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            if (!found) out.println("<p>No employee found with ID: " + idParam + "</p>");

        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }
    }
}
