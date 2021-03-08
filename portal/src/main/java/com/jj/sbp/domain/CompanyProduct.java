package com.jj.sbp.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CompanyProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public CompanyProduct(Company company, Product product) {
		this.company = company;
		this.product = product;
	}

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CompanyProduct companyProduct = (CompanyProduct) o;
		if (companyProduct.getProduct() == null || companyProduct.getCompany() == null)
			return false;

		boolean result = Objects.equals(this.company.getId(), companyProduct.getCompany().getId())
				&&
				Objects.equals(this.product.getId(), companyProduct.getProduct().getId());

		return result;
	}

	@Override
	public int hashCode() {
		int hashCode = 37 * ((this.company == null) ? 0 : Objects.hash(this.company.getId()));
		hashCode += 37 * ((this.product == null) ? 0 : Objects.hash(this.product.getId()));
		return hashCode;

//		return Objects.hash(name);
	}

}