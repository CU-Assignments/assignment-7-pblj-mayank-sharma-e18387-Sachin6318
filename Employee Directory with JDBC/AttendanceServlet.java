import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/AttendanceServlet")
public class AttendanceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("studentId");
        String name = request.getParameter("name");
        String date = request.getParameter("date");
        String status = request.getParameter("status");

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "root", "your_password")) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO attendance (student_id, name, date, status) VALUES (?, ?, ?, ?)");
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setString(3, date);
            stmt.setString(4, status);
            stmt.executeUpdate();

            request.setAttribute("studentName", name);
            RequestDispatcher dispatcher = request.getRequestDispatcher("attendance-success.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
