package com.jj.sbp.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	private String name;

	@OneToMany(mappedBy = "product")
	private Set<CompanyProduct> companies;// = new ArrayList<>();

	@Builder
	public Product(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Product product = (Product) o;
		return Objects.equals(name, product.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}