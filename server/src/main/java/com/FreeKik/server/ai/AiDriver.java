package com.FreeKik.server.ai;

import com.FreeKik.server.models.Club;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

import java.util.HashMap;
import java.util.Scanner;

public class AiDriver {
    public static void runAgent(HashMap<String, Club> clubs) {
        OpenAiChatModel model = OpenAiChatModel.builder()
                .apiKey(System.getenv("OPENAI_API_KEY"))
                .modelName("gpt-4o-mini")
                .temperature(0.2)
                .build();

        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(20);

        Agent agent = AiServices.builder(Agent.class)
                .chatLanguageModel(model)
                .tools(new AnalysisTool(clubs, model))
                .chatMemory(chatMemory)
                .build();

        //System.out.print("Dialog open\n");
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                //System.out.print("> ");
                String input = sc.nextLine();
                if ("exit".equalsIgnoreCase(input.trim())) break;
                String reply = agent.chat(input);
                System.out.println(reply);
            }
        }
    }
}
