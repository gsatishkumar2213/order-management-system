package org.example.kafka;

import java.time.LocalDateTime;

public record Tweet(String tweetId,
                    String userId,
                    String content,
                    LocalDateTime createdAt,
                    int likes) {
}
