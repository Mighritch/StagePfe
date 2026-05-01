import { TestBed } from '@angular/core/testing';

import { PretsParObjet } from './prets-par-objet';

describe('PretsParObjet', () => {
  let service: PretsParObjet;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PretsParObjet);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
