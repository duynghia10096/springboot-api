package com.DDN.login.security.service;

import com.DDN.login.model.User;
import com.DDN.login.repository.UserReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableGenerationService {

    @Autowired
    private UserReposity userReposity;

    public StringBuilder generateCommonHtmlHead() {
        StringBuilder stringBuilder = new StringBuilder();

        return stringBuilder.append("<head>")
                .append("<h1>Status<h1>")
                .append("</head>")
                .append("<body>")
                .append("<table border=1>")
                .append("<tr>")
                .append("<th>Author id</th><th>Authour Name</th><th>Content</th><th>Date</th>")
                .append("</tr>");
    }

    private void generateCommonFooter(StringBuilder stringBuilder) {
        stringBuilder.append("</table></body>");
    }
    public String generateReportMessage(List<User> userList) {
        StringBuilder stringBuilder = generateCommonHtmlHead();
        for(User user : userList) {
            stringBuilder.append("<tr>");
            stringBuilder.append("<td>").append(user.getUsername()).append("</td>");
            stringBuilder.append("<td>").append(user.getEmail()).append("</td>");
            stringBuilder.append("<td>").append(user.getId()).append("</td>");
            stringBuilder.append("<td>").append(user.getCreatedAt()).append("</td>");
            stringBuilder.append("</tr>");
        }
        generateCommonFooter(stringBuilder);
        return stringBuilder.toString();
    }

    public String generateReportMessage() {
        List<User> all = userReposity.findAll();
        return generateReportMessage(all);
    }
}
