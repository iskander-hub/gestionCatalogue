package org.sid.web;





import javax.validation.Valid;

import org.sid.dao.ProduitRepository;
import org.sid.entities.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProduitController {
	@Autowired
	private ProduitRepository produitRepository;
	
	@RequestMapping(value="/index")
	public String index(Model model,
			@RequestParam (name="page",defaultValue = "0")int p,
			@RequestParam (name="size",defaultValue = "5")int s,
			@RequestParam (name="mc",defaultValue = "")String mc) {
		PageRequest pageable = PageRequest.of(p, s);
		Page<Produit> pageProduits=produitRepository.chercher("%"+mc+"%",pageable);
		model.addAttribute("produits", pageProduits.getContent());
		int[] pages= new int[pageProduits.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("size", s);
		model.addAttribute("pageCourante", p);
		model.addAttribute("mc", mc);
		
		return "produits";
	}
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public String delete(Long id, String mc, int page, int size) {
		produitRepository.deleteById(id);
		return "redirect:/index?page="+page+"&size="+size+"&mc="+mc;
	}
	
	@RequestMapping(value="/form",method = RequestMethod.GET)
	public String formProduit(Model model) {
		model.addAttribute("produit",new Produit());
		return "formProduit";
	}
	@RequestMapping(value="/edit",method = RequestMethod.GET)
	public String editProduit(Model model,Long id) {
		Produit p = produitRepository.getOne(id);
		model.addAttribute("produit",p);
		return "editProduit";
	}
	@RequestMapping(value="/save",method = RequestMethod.POST)
	public String save(Model model, @Valid Produit produit, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return "formProduit";
		produitRepository.save(produit);
		return "confirmation";
	}
	@RequestMapping(value="/",method = RequestMethod.GET)	
	public String home() {
		return "redirect:/index";
	}
	@RequestMapping(value="/403",method = RequestMethod.GET)	
	public String accessDnied() {
		return "403";
	}

}
