package com.balmis.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.balmis.proyecto.model.Review;
import com.balmis.proyecto.repository.ReviewRepository;

@Service
public class ReviewService {
    

    @Autowired
    public ReviewRepository reviewRepository;

    // ************************
    // CONSULTAS
    // ************************ 
    @Transactional(readOnly = true)
    public List<Review> findAll(){
        return reviewRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Review findById(int id){
        return reviewRepository.findSqlByIdReview(id);
    }

    @Transactional(readOnly = true)
    public List<Review> findByUserId(int user_id){
        return reviewRepository.findSqlByIdUsuario(user_id);
    }

    @Transactional(readOnly = true)
    public List<Review> findByProductoId(int producto_id){
        return reviewRepository.findSqlByIdProducto(producto_id);
    }

    @Transactional(readOnly = true) 
    public Long count() {
        return reviewRepository.count();
    }

    @Transactional(readOnly = true) 
    public List<Review> findByIdGrThan(int num) {
        return reviewRepository.findSqlByIdGrThan(num);
    }
    
    // ************************
    // ACTUALIZACIONES
    // ************************ 
    @Transactional
    public Review save(Review review) {
        return reviewRepository.save(review);
    }


    @Transactional
    public Review update(int id, Review reviewDetails) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        
        if (reviewDetails.getReview() != null) {
            review.setReview(reviewDetails.getReview());
        }
        if (reviewDetails.getReview_date()  != null) {
            review.setReview_date(reviewDetails.getReview_date());
        }
        if (reviewDetails.getUsuario()  != null) {
            review.setUsuario(reviewDetails.getUsuario());
        }
        if (reviewDetails.getProducto()  != null) {
            review.setProducto(reviewDetails.getProducto());
        }

        return reviewRepository.save(review);
    }
    
    @Transactional
    public void deleteById(int id) {
        if (!reviewRepository.existsById(id)) {
            throw new RuntimeException("Review no encontrado");
        }
        reviewRepository.deleteById(id);
    }
}
