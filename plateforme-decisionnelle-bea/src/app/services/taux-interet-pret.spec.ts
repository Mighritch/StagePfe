import { TestBed } from '@angular/core/testing';

import { TauxInteretPret } from './taux-interet-pret';

describe('TauxInteretPret', () => {
  let service: TauxInteretPret;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TauxInteretPret);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
