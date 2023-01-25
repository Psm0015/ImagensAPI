package com.ImagensAPI.ImagensAPI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/buscarimg")
@CrossOrigin("*")
public class ImgController {
    
    private static String caminhoimg = "/img/";

    @Autowired
    private ImgRepository iRepository;

    @PostMapping("/")
    public ResponseEntity<String> postImg(MultipartFile arquivo){
        try {
            if(!arquivo.isEmpty()){
                byte[] bytes = arquivo.getBytes();
                Path caminho = Paths.get(caminhoimg+arquivo.getOriginalFilename());
                Files.write(caminho, bytes);
                ImgModel img = new ImgModel();
                img.setEndereco(caminhoimg+arquivo.getOriginalFilename());
                img.setNome(arquivo.getOriginalFilename());
                iRepository.save(img);
                return ResponseEntity.ok("Imagem Cadastrada com Sucesso");
            }else{
                return ResponseEntity.status(406).body("Arquivo Vazio");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.toString());
        }
    }
    @GetMapping("/")
    public @ResponseBody Iterable<ImgModel> fgetimgs(){
        return iRepository.findAll();
    }
    @GetMapping("/{id}")
    public byte[] getimg(@PathVariable Integer id) throws IOException{
        ImgModel imgm = iRepository.findById(id).get();
        File imagemarq = new File(imgm.getEndereco());
        return Files.readAllBytes(imagemarq.toPath());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> apgarprd(@PathVariable Integer id){
        try {
            iRepository.deleteById(id);
            return ResponseEntity.ok().body("Imagem deletada com sucesso com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Não foi possível deletar!");
        }

    }
}
