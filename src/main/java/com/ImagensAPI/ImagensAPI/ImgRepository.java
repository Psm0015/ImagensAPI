package com.ImagensAPI.ImagensAPI;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImgRepository extends JpaRepository<ImgModel, Integer>{
    public String findByNome(String nome);
}
