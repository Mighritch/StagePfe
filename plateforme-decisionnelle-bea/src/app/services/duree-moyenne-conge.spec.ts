import { TestBed } from '@angular/core/testing';

import { DureeMoyenneConge } from './duree-moyenne-conge';

describe('DureeMoyenneConge', () => {
  let service: DureeMoyenneConge;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DureeMoyenneConge);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
