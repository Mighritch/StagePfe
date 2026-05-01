import { TestBed } from '@angular/core/testing';

import { EncoursParTypePret } from './encours-par-type-pret';

describe('EncoursParTypePret', () => {
  let service: EncoursParTypePret;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EncoursParTypePret);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
