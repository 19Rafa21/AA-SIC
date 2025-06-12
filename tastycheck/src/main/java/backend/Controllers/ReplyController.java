package backend.Controllers;

import backend.Models.Reply;
import backend.Models.User;
import com.google.gson.Gson;
import backend.DTOs.ReplyDTO;
import backend.Services.ReplyService;
import backend.Services.UserService;
import org.orm.PersistentException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class ReplyController extends HttpServlet {

    private ReplyService replyService;
    private UserService userService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        this.replyService = new ReplyService();
        this.userService = new UserService();
        this.gson = new Gson();
    }

    // GET: /api/replies/review/{reviewId}
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo(); // /review/123

        if (path != null && path.startsWith("/review/")) {
            try {
                String reviewId = path.replace("/review/", "");
                List<Reply> replies = replyService.getRepliesByReviewId(reviewId);

                String json = gson.toJson(replies);
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(json);
            } catch (NumberFormatException | PersistentException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Caminho inválido");
        }
    }

    // POST: /api/replies/review/{reviewId}
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo(); // /review/123

        if (path != null && path.startsWith("/review/")) {
            try {
                Long reviewId = Long.parseLong(path.replace("/review/", ""));

                // 1️⃣ Lê o body
                String jsonPayload = lerJsonDoBody(request);

                // 2️⃣ Converte para DTO
                ReplyDTO dto = gson.fromJson(jsonPayload, ReplyDTO.class);

                // 3️⃣ Vai buscar o User
                User author = userService.findById(dto.getUserId());
                if (author == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Utilizador não encontrado");
                    return;
                }

                // 4️⃣ Cria a reply
                Reply reply = new Reply();
                reply.setText(dto.getText());
                reply.setAuthor(author);

                Reply created = replyService.createReply(reviewId, reply);

                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(gson.toJson(created));

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao criar resposta: " + e.getMessage());
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Caminho inválido");
        }
    }

    // PUT: /api/replies/{replyId}
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo(); // /15

        if (path != null && path.length() > 1) {
            try {
                Long replyId = Long.parseLong(path.substring(1));

                // 1️⃣ Lê o body
                String jsonPayload = lerJsonDoBody(request);

                // 2️⃣ Converte para DTO
                ReplyDTO dto = gson.fromJson(jsonPayload, ReplyDTO.class);

                // 3️⃣ Vai buscar o User
                User author = userService.findById(dto.getUserId());
                if (author == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Utilizador não encontrado");
                    return;
                }

                // 4️⃣ Atualiza a reply
                Reply updated = new Reply();
                updated.setText(dto.getText());
                updated.setAuthor(author);

                Reply result = replyService.updateReply(replyId, updated);

                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(gson.toJson(result));

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao atualizar resposta: " + e.getMessage());
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
        }
    }

    // DELETE: /api/replies/{replyId}
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo(); // /15

        if (path != null && path.length() > 1) {
            try {
                Long replyId = Long.parseLong(path.substring(1));
                replyService.deleteReply(replyId);
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID não especificado");
        }
    }

    // 🔁 Método auxiliar para ler o JSON do body
    private String lerJsonDoBody(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
