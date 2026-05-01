import { TestBed } from '@angular/core/testing';

import { CapitalRestantPret } from './capital-restant-pret';

describe('CapitalRestantPret', () => {
  let service: CapitalRestantPret;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CapitalRestantPret);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
