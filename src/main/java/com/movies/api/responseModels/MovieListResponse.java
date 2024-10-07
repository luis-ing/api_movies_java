package com.movies.api.responseModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieListResponse {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
}
