import { TestBed } from '@angular/core/testing';

import { PretsParDuree } from './prets-par-duree';

describe('PretsParDuree', () => {
  let service: PretsParDuree;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PretsParDuree);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
