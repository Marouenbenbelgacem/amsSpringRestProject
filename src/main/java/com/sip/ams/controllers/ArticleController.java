package com.sip.ams.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.sip.ams.entities.Article;
import com.sip.ams.repositories.ArticleRepository;
import com.sip.ams.repositories.ProviderRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping({ "/articles" })
@CrossOrigin(origins = "*") // n'import quelle front peut acceder a cette API
public class ArticleController {

	private final ArticleRepository articleRepository;
	private final ProviderRepository providerRepository;

	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/uploads";

	@Autowired
	public ArticleController(ArticleRepository articleRepository, ProviderRepository providerRepository) {
		this.articleRepository = articleRepository;
		this.providerRepository = providerRepository;
	}
	/*
	 * @Autowired private ArticleRepository articleRepository;
	 * 
	 * @Autowired private ProviderRepository providerRepository;
	 */

	@GetMapping("/list")
	public List<Article> getAllArticles() {
		return (List<Article>) articleRepository.findAll();
	}

	@PostMapping("/add/{providerId}")
	Article createArticle(@PathVariable(value = "providerId") Long providerId, @Valid @RequestBody Article article) {
		return providerRepository.findById(providerId).map(provider -> {
			article.setProvider(provider);
			return articleRepository.save(article);
		}).orElseThrow(() -> new IllegalArgumentException("ProviderId " + providerId + " not found"));
	}

	@PutMapping("/update/{providerId}/{articleId}")
	public Article updateArticle(@PathVariable(value = "providerId") Long providerId,
			@PathVariable(value = "articleId") Long articleId, @Valid @RequestBody Article articleRequest) {
		if (!providerRepository.existsById(providerId)) {
			throw new IllegalArgumentException("ProviderId " + providerId + " not found");
		}

		return articleRepository.findById(articleId).map(article -> {
			article.setPrice(articleRequest.getPrice());
			article.setLabel(articleRequest.getLabel());
			article.setPicture(articleRequest.getPicture());
			return articleRepository.save(article);
		}).orElseThrow(() -> new IllegalArgumentException("ArticleId " + articleId + "not found"));
	}

	@DeleteMapping("/delete/{articleId}")
	public ResponseEntity<?> deleteArticle(@PathVariable(value = "articleId") Long articleId) {
		return articleRepository.findById(articleId).map(article -> {
			articleRepository.delete(article);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new IllegalArgumentException("Article not found with id " + articleId));
	}
}
